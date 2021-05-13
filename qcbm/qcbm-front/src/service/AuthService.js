// Fetch API:
// https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API
// react项目使用cookie存储用户信息:
// https://blog.csdn.net/qq_39307284/article/details/104708450
// React + Fetch - HTTP POST Request Examples:
// https://jasonwatmore.com/post/2020/02/01/react-fetch-http-post-request-examples

class AuthService {

  login(loginData) {

    let result = false;

    fetch("/api/auth/signin", {
      method: "POST",
      body: JSON.stringify(loginData),
      headers: new Headers({
        'Content-Type': 'application/json;charset=utf-8'
      })
    }).then(function(response) {
      return response.json()
    }).then(function(data) {
      console.log(data)
      localStorage.setItem("user", JSON.stringify(data));
      result = true;
    });

    return result;
  }


  logout() {
    localStorage.removeItem("user");
  }


  saveUser(data){
    localStorage.setItem("user", JSON.stringify(data));
  }


  register(registerData) {

    fetch("/api/auth/signup", {
      method: "POST",
      body: JSON.stringify(registerData),
      headers: new Headers({
        'Content-Type': 'application/json;charset=utf-8'
      })
    }).then(function (response) {
      return response.json()
    })
  }


  getCurrentUser() {
    let user = JSON.parse(localStorage.getItem('user'));
    // console.log(user);
    return user;
  }
}

export default new AuthService();
