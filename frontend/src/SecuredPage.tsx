import React from 'react';
import axios from "axios";

type SecuredPageProps = {
    username: string,
    onLogout: () => void
};

function SecuredPage(props: SecuredPageProps) {
    const logout = () => {
        axios.get("/api/app-users/logout").then(() => console.log("Logged out")).then(props.onLogout)
    }

    return (<>

        <div>Welcome, {props.username}</div>
        <button onClick={logout}>Logout</button>

        </>
    );
}

export default SecuredPage;