import {useState} from "react";
import axios from "axios";

type LoginPageProps = {
    onLogin: () => void,
}
export default function LoginPage(props: LoginPageProps) {
    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [registrationErrorMessage, setRegistrationErrorMessage] = useState({
        password: undefined,
        username: undefined
    })


    const login = () => {
        axios.get("/api/app-users/login", {
            auth: {
                username,
                password
            }
        } //TODO: Fragen, wieso es mit () => props.onlogin keinen Reload gibt
        ).then(props.onLogin)
    }

    const register = () => {
        axios.post("/api/app-users", {username, password})
            .catch(response => {
                setRegistrationErrorMessage(response.response.data)
            })
        }


    return (
        <>
        <label htmlFor={"username"}>Username</label>
        <input required id={"username"} type={"text"} onChange={event => setUsername(event.target.value)}/>
            {registrationErrorMessage && <p>{registrationErrorMessage.username}</p>}
        <label htmlFor={"password"}>Password</label>
        <input required id={"password"} type={"password"} onChange={event => setPassword(event.target.value)}/>
            {registrationErrorMessage && <p>{registrationErrorMessage.password}</p>}
            <button onClick={() => login()}>Login</button>
            <button onClick={() => register()}>Register</button>

        </>
    )
}