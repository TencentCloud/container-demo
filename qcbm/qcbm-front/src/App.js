// import logo from './logo.svg';
import './App.css';
import React from 'react'
import {
  BrowserRouter as Router,
  Switch,
  Route
} from 'react-router-dom'

import Home from './pages/Home'
import SignUp from './pages/SignUp'
import SignIn from './pages/SignIn'
import Favorites from './pages/Favorites'
import Bookshelf from './pages/Bookshelf'
import Navbar from './components/Navbar'
// import UserProfile from './components/UserProfile'
import AuthService from './service/AuthService'

class App extends React.Component {

  constructor(props){
    super(props);
    this.state = {
      loggedIn: false,
      backend: ""
    };
  }

  componentDidMount() {

    const appCompent = this;

    fetch("/api/sysinfo/backend")
      .then(response => response.json())
      .then(function(data){
        // console.log("/api/sysinfo/backend: ", data);
        appCompent.setState({ 'backend': data.backend});
      });

    // let compHome = this;
    let currentUser = AuthService.getCurrentUser();
    if (currentUser && currentUser.id) {
      appCompent.setState({ loggedIn: true })
    }
  }

  render(){
    return (
      <Router>
        <div className="App">

          <Navbar loggedIn={this.state.loggedIn} backend={this.state.backend}/>

          {/* <nav className="navbar site-header justify-content-between sticky-top">
            <NavLink to="/" className="navbar-brand ml-3">
              <img src="./pic/logo-dubbo.png" style={{width:"300px", height:"79px"}} alt=""/>
              <span style={{ color: "#fff", fontSize: "0.8rem", verticalAlign: "bottom" }}>Dubbo on TKE</span>
            </NavLink>

            <div style={{marginTop: "2rem", fontSize: "1.125rem", marginRight: "3rem", horizAlign: "right"}}>
              <NavLink to="/bookshelf" style={{marginLeft: "2rem"}}>Bookshelf</NavLink>
              <NavLink to="/facorites" style={{ marginLeft: "2rem" }}>Favorites</NavLink>

              {
                this.state.loggedIn
                ? <UserProfile/>
                : <span>
                    <NavLink to="/signup" style={{ marginLeft: "2rem" }}>Sign Up</NavLink>
                    <NavLink to="/signin" style={{ marginLeft: "2rem" }}>Sign In</NavLink>
                  </span>
              }
            </div>
          </nav> */}

          <div>
            <Switch>
              <Route exact path="/" component={Home} />
              {/* <Route exact path="/facorites" component={Favorites} /> */}
              <Route exact path="/facorites" >
                {
                  this.state.loggedIn 
                  ? <Favorites /> 
                  : <SignIn setLoginState={(k, v) => { this.setState({ [k]: v }) }} />
                }
              </Route>
              <Route exact path="/bookshelf">
                {
                  this.state.loggedIn 
                  ? <Bookshelf /> 
                  : <SignIn setLoginState={(k, v) => { this.setState({ [k]: v }) }} />
                }
              </Route>
              <Route exact path="/signin" >
                <SignIn setLoginState={(k, v)=>{ this.setState({[k]:v}) }}/>
              </Route>
              <Route exact path="/signup" component={SignUp} />
            </Switch>
          </div>
        </div>
      </Router>
    );
  }
}

export default App;
