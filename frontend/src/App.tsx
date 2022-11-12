import React, {useEffect, useState} from 'react';
import './App.css';
import axios from "axios";
import ClipLoader from "react-spinners/ClipLoader";
import LoginPage from "./LoginPage";
import SecuredPage from "./SecuredPage";

function App() {
  const [username, setUsername] = useState<string>();

  const fetchUsername = () => {
    axios.get("/api/app-users/me")
        .then(response => response.data)
        .then(setUsername);
  }
  useEffect(fetchUsername, [])

  if(username === undefined) {
    return <ClipLoader
        size={150}
        aria-label="Loading Spinner"
        data-testid="loader"
    />
  }
  if(username === "anonymousUser") {
    return <LoginPage onLogin={fetchUsername}></LoginPage>
  }
  return <SecuredPage onLogout={fetchUsername} username={username}></SecuredPage>

}

export default App;
