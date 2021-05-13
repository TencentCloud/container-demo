import React from 'react';

class SignUp extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: '',
            password: '',
            // rememberMe: false
        };
    }

    saveData = (field) => {
        return (event) => {
            this.setState({ [field]: event.target.value })
        }
    }

    handleSubmit = (e) => {
        e.preventDefault()

        let signupData = {};
        signupData.name = this.state.name;
        signupData.password = this.state.password;

        fetch("/api/auth/signup", {
            method: "POST",
            body: JSON.stringify(signupData),
            headers: new Headers({
                'Content-Type': 'application/json;charset=utf-8'
            })
        }).then(function (response) {
            return response.json()
        }).then(function (data) {
            // console.log(data);
            // tmp.props.history.push("/signin");
            window.location.href = "signin";
        });
    }

    render() {
        return (
            <div>
                <form className="form-signin" onSubmit={this.handleSubmit}>
                    {/* <img className="login-logo" src="pic/logo-white.png" alt=""/> */}
                    <h1 className="h3 mb-3 mt-5 font-weight-normal">Please sign up</h1>
                    <label htmlFor="inputEmail" className="sr-only">Account</label>
                    <input 
                        onChange={this.saveData('name')}
                        name="name"
                        type="text" 
                        id="inputEmail" 
                        className="form-control" 
                        placeholder="Account" 
                        required 
                        autoFocus
                    />
                    <label htmlFor="inputPassword" className="sr-only">Password</label>
                    <input 
                        onChange={this.saveData('password')}
                        name="password"
                        type="password" 
                        id="inputPassword" 
                        className="form-control" 
                        placeholder="Password" 
                        required 
                    />
                    <button className="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
                </form>
            </div>
        );
    }
};

export default SignUp