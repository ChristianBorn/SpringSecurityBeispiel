import React from 'react';

type UserPageProps = {
    username: string,
    eMail: string
}

function UserPage(props: UserPageProps) {
    return  <>
        <div>Username: {props.username}</div>
        <div>Email: {props.eMail}</div>
        </>
    ;
}

export default UserPage;