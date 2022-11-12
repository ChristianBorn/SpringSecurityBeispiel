import {useState} from "react";
import axios from "axios";

type LoginPageProps = {
    onLogin: () => void,
}
export default function LoginPage(props: LoginPageProps) {
    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")


    const login = () => {
        axios.get("/api/app-users/login", {
            auth: {
                username,
                password
            }
        }
        ).then(() => props.onLogin)
    }

    const register = () => {
        return null
    }

    return (
        <>
        <label htmlFor={"username"}>Username</label>
        <input id={"username"} type={"text"} onChange={event => setUsername(event.target.value)}/>
        <label htmlFor={"password"}>Password</label>
        <input id={"password"} type={"password"} onChange={event => setPassword(event.target.value)}/>
            <button onClick={() => login()}>Login</button>
            <button onClick={() => register()}>Register</button>
        </>
    )
}