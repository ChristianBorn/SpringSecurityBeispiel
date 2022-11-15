import React, {useState} from "react";
import axios from "axios";

type LoginPageProps = {
    onLogin: () => void,
}
export default function LoginPage(props: LoginPageProps) {
    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [registrationErrorMessage, setRegistrationErrorMessage] = useState({
        password: undefined,
        username: undefined,
        userAlreadyExists: undefined
    })
    const [successMessage, setSuccessMessage] = useState<string>("")


    const login = () => {
        axios.get("/api/app-users/login", {
            auth: {
                username,
                password
            }
        }
        ).then(props.onLogin)
    }

    const register = () => {
        axios.post("/api/app-users", {username, password})
            .then(response => response.data)
            .then(data =>setSuccessMessage(data))
            .catch(response => {
                setRegistrationErrorMessage(response.response.data)
            })
        }


    return (
        <>
        <label htmlFor={"username"}>Username</label>
        <input required id={"username"} type={"text"} onChange={event => setUsername(event.target.value)}/>
            {registrationErrorMessage.username && <p>{registrationErrorMessage.username}</p>}
        <label htmlFor={"password"}>Password</label>
        <input required id={"password"} type={"password"} onChange={event => setPassword(event.target.value)}/>
            {registrationErrorMessage.password && <p>{registrationErrorMessage.password}</p>}
            <button onClick={() => login()}>Login</button>
            <button onClick={() => register()}>Register</button>
            {successMessage && <p>{successMessage}</p>}
            {registrationErrorMessage.userAlreadyExists && <p>{registrationErrorMessage.userAlreadyExists}</p>}

            </>

    )
}