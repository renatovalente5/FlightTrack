import React from "react";
import './App.css';
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';
import PlaneComponent from "./components/PlaneComponent";
import IP_PlaneComponent from "./components/IP_PlaneComponent";
import IP_SpainComponent from "./components/IP_SpainComponent";
import IP_PortugalComponent from "./components/IP_PortugalComponent";
import IP_OverComponent from "./components/IP_OverComponent";
import IP_EntryComponent from "./components/IP_EntryComponent";
import Test from "./components/Test";

class App extends React.Component {
  render() {
    return (
    <Router>
        <div>
        
        <h2>Flight Tracker</h2>
          <nav className="navbar navbar-expand-lg navbar-light bg-light">
          <ul className="navbar-nav mr-auto">
            <li><Link to={'/planes'} className="nav-link"> Planes </Link></li>
            <li><Link to={'/ip'} className="nav-link">Origin in the Irebian Peninsula</Link></li>
            <li><Link to={'/portugal'} className="nav-link">Origin in Portugal</Link></li>
            <li><Link to={'/spain'} className="nav-link">Origin in Spain</Link></li>
            <li><Link to={'/over'} className="nav-link">Over the Irebian Peninsula</Link></li>
            <li><Link to={'/entry'} className="nav-link">Entered the Irebian Peninsula</Link></li>
            <li><Link to={'/test'} className="nav-link">Test</Link></li>
          </ul>
          </nav>
          <hr />
          <Switch>
              <Route exact path='/planes' component={PlaneComponent} />
              <Route path='/ip' component={IP_PlaneComponent} />
              <Route path='/spain' component={IP_SpainComponent} />
              <Route path='/portugal' component={IP_PortugalComponent} />
              <Route path='/over' component={IP_OverComponent} />
              <Route path='/entry' component={IP_EntryComponent} />
              <Route path='/test' component={Test} />

          </Switch>
        </div>
      </Router>
    );
  }
}

export default App;