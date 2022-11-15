import React, {useEffect, useState} from 'react';
import './App.css';
import axios from "axios";
import ClipLoader from "react-spinners/ClipLoader";
import LoginPage from "./LoginPage";
import SecuredPage from "./SecuredPage";
import {HashRouter, NavLink, Route, Routes} from "react-router-dom";
import UserPage from "./UserPage";

function App() {
  const [userDetails, setUserDetails] = useState({
      username: "anonymousUser",
      eMail: ""
  });

  const fetchUsername = () => {
    axios.get("/api/app-users/me")
        .then(response => response.data)
        .then(setUserDetails);
  }
  useEffect(fetchUsername, [])

  if(userDetails === undefined) {
    return <ClipLoader
        size={150}
        aria-label="Loading Spinner"
        data-testid="loader"
    />
  }
  if(userDetails.username === "anonymousUser") {
    return <LoginPage onLogin={fetchUsername}></LoginPage>
  }
  return (
      <HashRouter basename="/">
        <NavLink to={'/me'}>Link to me</NavLink>
        <Routes>
            <Route path={'/'} element={<SecuredPage onLogout={fetchUsername} username={userDetails.username}/>}></Route>
          <Route path={'/me'} element={<UserPage username={userDetails.username} eMail={userDetails.eMail}/>}></Route>
        </Routes>
        </HashRouter>
  )

}

export default App;
