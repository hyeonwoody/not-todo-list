import React from "react";
import "./App.css";
import styled from "styled-components";

import Task from "./components/Task.tsx"
import Kanban from "./components/Kanban.tsx"
import Logo from './assets/Logo.png';

function App() {
    const Background = styled.div`
    background-color: #1E1E1E;
    `
    return (
        <div className="App">
            <Background>
                <img src={Logo}/>

                <Kanban/>
            </Background>

        </div>
    );
}

export default App;