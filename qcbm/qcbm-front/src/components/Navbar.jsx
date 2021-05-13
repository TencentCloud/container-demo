import React from 'react'
import {NavLink} from 'react-router-dom'
import UserProfile from './UserProfile'

class Navbar extends React.Component {

    render(){
        return(
            <>
            {
                this.props.backend === "dubbo"
                ?
                <nav className="navbar site-header justify-content-between sticky-top">
                    <NavLink to="/" className="navbar-brand ml-3">
                        <img src="./pic/logo-dubbo.png" style={{ width: "300px", height: "79px" }} alt="" />
                        <span style={{ color: "#fff", fontSize: "0.8rem", verticalAlign: "bottom" }}>Dubbo on TKE</span>
                    </NavLink>

                    <div style={{ marginTop: "2rem", fontSize: "1.125rem", marginRight: "3rem", horizAlign: "right" }}>
                        <NavLink to="/bookshelf" style={{ marginLeft: "2rem" }}>Bookshelf</NavLink>
                        <NavLink to="/facorites" style={{ marginLeft: "2rem" }}>Favorites</NavLink>

                        {
                            this.props.loggedIn
                                ? <UserProfile />
                                : <span>
                                    <NavLink to="/signup" style={{ marginLeft: "2rem" }}>Sign Up</NavLink>
                                    <NavLink to="/signin" style={{ marginLeft: "2rem" }}>Sign In</NavLink>
                                </span>
                        }
                    </div>
                </nav>
                : 
                <nav className="navbar site-header justify-content-between sticky-top" style={{ backgroundColor: "#6db33f" }}>
                    <NavLink to="/" className="navbar-brand ml-3">
                        <img src="./pic/logo-sc.png" style={{ width: "300px", height: "79px" }} alt="" />
                            <span style={{ color: "#fff", fontSize: "0.8rem", verticalAlign: "bottom" }}>Spring Coud on TKE</span>
                    </NavLink>

                    <div style={{ marginTop: "2rem", fontSize: "1.125rem", marginRight: "3rem", horizAlign: "right" }}>
                        <NavLink to="/bookshelf" style={{ marginLeft: "2rem" }}>Bookshelf</NavLink>
                        <NavLink to="/facorites" style={{ marginLeft: "2rem" }}>Favorites</NavLink>

                        {
                            this.props.loggedIn
                                ? <UserProfile />
                                : <span>
                                    <NavLink to="/signup" style={{ marginLeft: "2rem" }}>Sign Up</NavLink>
                                    <NavLink to="/signin" style={{ marginLeft: "2rem" }}>Sign In</NavLink>
                                </span>
                        }
                    </div>
                </nav>
            }
            </>
        )
    }
};

export default Navbar;