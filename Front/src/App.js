import React, { Component } from 'react'
import './App.css'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider'
import Chat from './components/Chat'

class App extends Component {

  render() {
    return (
      <MuiThemeProvider style={{height: "100%"}}>
        <Chat />
      </MuiThemeProvider>
    )
  }
}

export default App
