import React from 'react'
import AuthService from '../service/AuthService'

class UserProfile extends React.Component {

    logout = ()=>{
        AuthService.logout();
        window.location.href="/";
    }

    render(){
        let currentUser = AuthService.getCurrentUser();
        // console.log(currentUser);

        return (
            <span style={{ color: "#d9d9d9", marginLeft: "2rem" }}>
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" className="bi bi-person-circle" viewBox="0 0 16 16" style={{ paddingRight: "0.5rem", paddingBottom: "0.5rem" }}>
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" />
                    <path fillRule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z" />
                </svg>
                {
                    currentUser && currentUser.id
                        ? <>
                            {currentUser.name}
                            <span onClick={this.logout} className="logoutbtn" style={{ marginLeft: "1.5rem" }}>Logout</span>
                        </>
                        : 'Welcome'
                }

            </span>
        )
    }
};

export default UserProfile;