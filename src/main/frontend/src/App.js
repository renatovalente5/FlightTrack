import React from "react";
import './App.css';
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';
import IP_OverComponent from "./components/IP_OverComponent";
import IP_PlaneComponent from "./components/IP_PlaneComponent";
import IP_EntryComponent from "./components/IP_EntryComponent";
import Test from "./components/Test";
import MapComponent from "./components/MapComponent";
import Palha from "./components/Palha";
import styled from 'styled-components';

const H0 = styled.h1({
    fontSize: 25,
    fontWeight: 'bold',
    paddingBottom: 2,
    paddingTop: 20,
    color: 'black',
    textAlign: "center"
});

const H1 = styled.h1({
    fontSize: 15,
    paddingBottom: 20,
    paddingTop: 0,
    color: 'gray',
    textAlign: "center"
});
        
class App extends React.Component {
    render() {
        return (
            <Router>
                <div>
                    <H0 ><i>Flight Tracker</i></H0>
                    <H1 > flights from the Iberian Peninsula</H1>
        
                    <nav className="navbar navbar-expand-lg navbar-light bg-light">
                        <ul className="navbar-nav mr-auto">
                            <li><Link to={'/ip'} className="nav-link">Origin </Link></li>
                            <li><Link to={'/over'} className="nav-link">Over </Link></li>
                            <li><Link to={'/entry'} className="nav-link">Entered</Link></li>
                            <li><Link to={'/test'} className="nav-link">Test</Link></li>
                            <li><Link to={'/palha'} className="nav-link">PALHA</Link></li>
                        </ul>
                    </nav>
                    <hr />
                    <Switch>
                        <Route path='/ip' component={IP_PlaneComponent} />
                        <Route path='/over' component={IP_OverComponent} />
                        <Route path='/entry' component={IP_EntryComponent} />
                        <Route path='/test' component={Test} />
                        <Route path='/palha' component={Palha} />
                    </Switch>
                </div>
            </Router>
        );
    }
}

export default App;