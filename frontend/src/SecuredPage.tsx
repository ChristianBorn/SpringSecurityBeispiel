import React from 'react';

type SecuredPageProps = {
    username: string,
    onLogout: () => void
};

function SecuredPage(props: SecuredPageProps) {

    return (
        <div>Welcome, {props.username}</div>
    );
}

export default SecuredPage;