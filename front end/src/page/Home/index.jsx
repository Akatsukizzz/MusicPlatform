import React from "react";
import {Row, Col, Radio} from 'antd';
import './style.css'
import { BrowserRouter as Router, Route, NavLink, Switch, Redirect } from "react-router-dom";
import { renderRoutes } from 'react-router-config'
import SongList from "./SongList";
import Recommend from "./Recommend";
import NewSong from "./NewSong";
import Songer from "./Songer";
import MusicRadio from "./MusicRadio"
import RanList from "./RankList";


class Home extends React.Component {
    constructor(props) {
        super(props);
        // this.state = {
        //     route: props.route,
        // }
    }
    // fun() {
    //     console.log(this.state.route);
    //     console.log(this.state.route.children);
    // }

    render() {
        return(
            <Router>
                {/*主页顶部路由*/}
                <Row align="middle" className="home-nav">
                    <Col span={16} offset={6} className="home-child-nav">
                        <ul>
                            <li>
                                <NavLink activeClassName="child-selected" to="/home/recommend">推荐</NavLink>
                            </li>
                            <li>
                                <NavLink activeClassName="child-selected" to="/home/ranklist">排行榜</NavLink>
                            </li>
                            <li>
                                <NavLink activeClassName="child-selected" to="/home/songlist">歌单</NavLink>
                            </li>
                            <li>
                                <NavLink activeClassName="child-selected" to="/home/musicradio">主播电台</NavLink>
                            </li>
                            <li>
                                <NavLink activeClassName="child-selected" to="/home/songer">歌手</NavLink>
                            </li>
                            <li>
                                <NavLink activeClassName="child-selected" to="/home/newsong">新碟上架</NavLink>
                            </li>
                        </ul>
                    </Col>
                </Row>

                <Switch>
                    <Redirect exact path="/home" to="home/recommend" />
                    <Route exact path="/home/recommend" component={ Recommend }/>
                    {/* 二级路由方式暂时不用，等后面解决这个bug */}
                    {/*{ renderRoutes(this.state.route.children) }*/}
                    <Route exact path="/home/songlist" component={ SongList }/>
                    <Route exact path="/home/newsong" component={ NewSong }/>
                    <Route exact path="/home/songer" component={ Songer }/>
                    <Route exact path="/home/songlist" component={ SongList }/>
                    <Route exact path="/home/musicradio" component={ MusicRadio }/>
                    <Route exact path="/home/ranklist" component={ RanList }/>
                </Switch>
            </Router>
        )
    }
}


export default Home
