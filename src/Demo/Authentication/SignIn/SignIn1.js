import React, { PureComponent } from "react";
import { NavLink } from "react-router-dom";
import axios from "axios";
import "./../../../assets/scss/style.scss";
import Aux from "../../../hoc/_Aux";
import Breadcrumb from "../../../App/layout/AdminLayout/Breadcrumb";
import { withRouter } from "react-router-dom";

class SignUp1 extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      emailOrPseudo: "",
      password: ""
    };

    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleInputChange(event) {
    const target = event.target;
    const value = target.type === "checkbox" ? target.checked : target.value;
    const name = target.name;

    this.setState({
      [name]: value
    });
  }

  handleSubmit(event) {
    event.preventDefault();
    console.log(this.state);
    this.login(this.state);
  }
  componentDidMount() {}
  setAuthToken = token => {
    if (token) {
      axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
      console.log(token);
    } else delete axios.defaults.headers.common["Authorization"];
  };
  login = data => {
    const { history } = this.props;

    axios
      .post("http://localhost:8080/authenticate", data)
      .then(response => {
        let token = response.data.token;
        localStorage.setItem("jwToken", token);
        this.setAuthToken(token);
        this.props.history.push("/home");
        console.log(token);
      })
      .catch(function(error) {
        if (history) history.push("/");
        // console.log(error.response.data);
      });
  };

  render() {
    return (
      <Aux>
        <Breadcrumb />
        <div className="auth-wrapper">
          <div className="auth-content">
            <div className="auth-bg">
              <span className="r" />
              <span className="r s" />
              <span className="r s" />
              <span className="r" />
            </div>
            <div className="card">
              <div className="card-body text-center">
                <div className="mb-4">
                  <i className="feather icon-unlock auth-icon" />
                </div>
                <h3 className="mb-4">Login</h3>
                <form onSubmit={this.handleSubmit}>
                  <div className="input-group mb-3">
                    <input
                      type="text"
                      id="email"
                      name="emailOrPseudo"
                      placeholder="Email"
                      value={this.state.emailOrPseudo}
                      onChange={this.handleInputChange}
                      className="form-control"
                    />
                  </div>
                  <div className="input-group mb-4">
                    <input
                      type="password"
                      className="form-control"
                      placeholder="password"
                      id="password"
                      name="password"
                      value={this.state.password}
                      onChange={this.handleInputChange}
                    />
                  </div>
                  <div className="form-group text-left">
                    <div className="checkbox checkbox-fill d-inline">
                      <input
                        type="checkbox"
                        name="checkbox-fill-1"
                        id="checkbox-fill-a1"
                      />
                      <label htmlFor="checkbox-fill-a1" className="cr">
                        {" "}
                        Remember me
                      </label>
                    </div>
                  </div>
                  <button
                    className="btn btn-primary shadow-2 mb-4"
                    type="submit"
                  >
                    Login
                  </button>
                </form>
                <p className="mb-2 text-muted">
                  Forgot password?{" "}
                  <NavLink to="/auth/reset-password-1">Reset</NavLink>
                </p>
                <p className="mb-0 text-muted">
                  Don’t have an account?{" "}
                  <NavLink to="/auth/signup-1">Signup</NavLink>
                </p>
              </div>
            </div>
          </div>
        </div>
      </Aux>
    );
  }
}

export default withRouter(SignUp1);
