import React, { Component } from 'react';
import Paper from 'material-ui/Paper';
import {List, ListItem} from 'material-ui/List';
import TextField from 'material-ui/TextField';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import config from "../config";

const styles = {
  container: {
    width: 700,
    height: "90%",
    marginLeft: -350,
    position: "fixed",
    left: "50%",
    bottom: 50,
  },
  dialogField: {
    width: 680,
    height: "90%",
    margin: "20px 10px 0 10px",
    overflow: "auto",
  },
  submitField: {
    width: 650,
    position: "fixed",
    marginLeft: 25,
    bottom: 60,
  },
};

export default class Chat extends Component {
  constructor(props) {
    super(props);
    this.state = {
      dialog: [],
      text: "",
      signUp: false,
      loginField: "",
      passwordField: "",
    };
  }

  componentDidMount() {
    this.state.ws = new WebSocket(config.wsUrl);
    this.state.ws.onopen = function (event) { };

    this.state.ws.onmessage = function (event) {
      const dt = event.data;
      console.log(dt);
      this.setState(prev => {
        prev.dialog.push(JSON.parse(dt));
        return prev;
      });
    }.bind(this);

    this.state.ws.onclose = function (event) { };
  }

  handleSubmit = (event) => {
    if (event.key === 'Enter' && this.state.text != "") {
      this.state.ws.send(sessionStorage.getItem("token") + "::" + this.state.text);
      this.state.text = "";
    }
  };

  handleChangeMsg = (event) => {
    this.setState({
      text: event.target.value,
    });
  };

  handleChangeLogin = (event) => {
    this.setState({
      loginField: event.target.value,
    });
  };

  handleChangePassword = (event) => {
    this.setState({
      passwordField: event.target.value,
    });
  };

  handleSignIn = () => {
    if (this.state.loginField != "" && this.state.passwordField != "") {
      fetch(config.host + `signin?login=${this.state.loginField}&password=${this.state.passwordField}`, {
        method: "POST"
      })
        .then((res) => {
          return res.text();
        })
        .then(token => {
          console.log(token);
          sessionStorage.setItem("token", token);
          this.forceUpdate();
        })
    };
  };

  handleWishToSignUp = () => {
    console.log(true);
    this.setState({
      signUp: true,
    });
  };

  handleSignUp = () => {
    if (this.state.loginField != "" && this.state.passwordField != "") {
      fetch(config.host + `signup?login=${this.state.loginField}&password=${this.state.passwordField}`, {
        method: "POST"
      })
        .then((res) => {
          this.setState({
            signUp: false,
          });
        });
    };
  };

  render() {
    const listItem = this.state.dialog.map(i => {
      return <ListItem primaryText={<a><b>{i.sender + ': '}</b>{i.msg}</a>} />
    });

    return (
      <Paper style={styles.container} zDepth={2}>
        <div style={styles.dialogField}>
          <List>
            {listItem}
          </List>
        </div>
        <div style={styles.submitField}>
          <TextField
            onKeyPress={this.handleSubmit}
            onChange={this.handleChangeMsg}
            hintText="Введите сообщение..."
            value={this.state.text}
            fullWidth={true}
          />
        </div>
        {(!sessionStorage.getItem("token") && !this.state.signUp) && (
          <Dialog
            contentStyle={{width: 400}}
            title="Пожалуйста авторизируйтесь"
            actions={[
              <FlatButton
                label="Зарегистрироваться"
                primary={false}
                keyboardFocused={false}
                onClick={this.handleWishToSignUp}
              />,
              <FlatButton
                label="Войти"
                primary={true}
                keyboardFocused={false}
                onClick={this.handleSignIn}
              />
            ]}
            modal={true}
            open={true}
          >
            <TextField
              hintText="Логин"
              onChange={this.handleChangeLogin}
              value={this.state.loginField}
              fullWidth={true}
            /><br />
            <TextField
              hintText="Пароль"
              onChange={this.handleChangePassword}
              value={this.state.passwordField}
              type="password"
              fullWidth={true}
            /><br />
          </Dialog>
        )}
        {this.state.signUp && (
          <Dialog
            contentStyle={{width: 400}}
            title="Регистрация"
            actions={[
              <FlatButton
                label="Зарегистрироваться"
                primary={true}
                keyboardFocused={false}
                onClick={this.handleSignUp}
              />
            ]}
            modal={true}
            open={true}
          >
            <TextField
              hintText="Логин"
              onChange={this.handleChangeLogin}
              value={this.state.loginField}
              fullWidth={true}
            /><br />
            <TextField
              hintText="Пароль"
              onChange={this.handleChangePassword}
              value={this.state.passwordField}
              type="password"
              fullWidth={true}
            /><br />
          </Dialog>
        )}
      </Paper>
    )
  }
}
