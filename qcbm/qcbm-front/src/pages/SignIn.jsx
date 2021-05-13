import React from 'react';
import { withRouter } from 'react-router-dom';
import AuthService from '../service/AuthService'

class SignIn extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      name: '',
      password: '',
      // rememberMe: false
    };
  }

  saveData = (field)=>{
    return (event)=>{
      this.setState({[field] : event.target.value})
    }
  }

  handleSubmit = (e)=>{
    e.preventDefault()

    let loginData = {};
    loginData.name = this.state.name;
    loginData.password = this.state.password;

    const tmp = this;

    fetch("/api/auth/signin", {
      method: "POST",
      body: JSON.stringify(loginData),
      headers: new Headers({
        'Content-Type': 'application/json;charset=utf-8'
      })
    }).then(function (response) {
      return response.json()
    }).then(function (data) {
      // console.log("user login: ", data);
      AuthService.saveUser(data);
      // localStorage.setItem("user", JSON.stringify(data));
      tmp.props.setLoginState('loggedIn', true);
      tmp.props.history.push("/");
    });
  }

  render(){
    return (
      <div>
        <form className="form-signin" onSubmit={this.handleSubmit}>
          <h1 className="h3 mb-3 mt-5 font-weight-normal">Please sign in</h1>
          <label htmlFor="inputName" className="sr-only">Account</label>
          <input 
            onChange={this.saveData('name')} 
            name="name" 
            type="text" 
            id="inputName" 
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
          <div className="checkbox mb-3">
            {/* <label>
              <input 
                onChange={(event)=>{
                  this.setState({['rememberMe']: event.target.checked})
                }} 
                type="checkbox"
              />
              Remember me
            </label> */}
          </div>
          <button className="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </form>
      </div>
    );
  }
};

export default withRouter(SignIn);