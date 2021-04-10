import React from "react";
import './App.css';
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';
import IP_OverComponent from "./components/IP_OverComponent";
import MapComponent from "./components/MapComponent";
import DataPlaneComponent from "./components/DataPlaneComponent";
import HistoricComponent from "./components/HistoricComponent";
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
                            <li><Link to={'/map'} className="nav-link"><b>Map</b></Link></li>
                            <li><Link to={'/over'} className="nav-link"><b>Iberian Peninsula</b> </Link></li>
                            <li><Link to={'/historic'} className="nav-link"><b>Historic</b></Link></li>
                        </ul>
                    </nav>
                    <hr />
                    <Switch>
                        <Route path='/map' component={MapComponent} />
                        <Route path='/over' component={IP_OverComponent} />
                        <Route path='/historic' component={HistoricComponent} />
                        <Route path='/:id' component={DataPlaneComponent} />
                    </Switch>
                </div>
            </Router>
        );
    }
}

export default App;