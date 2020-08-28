import React from "react";
import Footer from "../../../components/Footer";
import './style.css'

const RanList = () => {
    return(
        <div>
           <div className='rankwrapper'>
              <div className='rankleft'>
                  <h2>云音乐特色榜</h2>
                  <ul className='rank-special'>
                      <li>
                          <a>
                          <img src="http://p1.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>云音乐飙升榜</span>
                            <p>
                                每天更新
                            </p>
                          </div>

                          </a>
                      </li>
                      <li>
                          <a>
                          <img src="http://p2.music.126.net/N2HO5xfYEqyQ8q6oxCw8IQ==/18713687906568048.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>云音乐新歌榜</span>
                            <p>
                                每天更新
                            </p>
                          </div>

                          </a>
                      </li><li>
                          <a>
                          <img src="http://p2.music.126.net/GhhuF6Ep5Tq9IEvLsyCN7w==/18708190348409091.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>云音乐热歌榜</span>
                            <p>
                                每天更新
                            </p>
                          </div>

                          </a>
                      </li>
                  </ul>
                  <h2>全球媒体榜</h2>
                  <ul className='rank-media'>
                  <li>
                          <a>
                          <img src="http://p2.music.126.net/y8zyTt4HwXbJqB31aQKz1A==/109951164353220919.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>云音乐说唱榜</span>
                            <p>
                                每天更新
                            </p>
                          </div>

                          </a>
                      </li><li>
                          <a>
                          <img src="http://p2.music.126.net/BzSxoj6O1LQPlFceDn-LKw==/18681802069355169.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>云音乐古典音乐榜</span>
                            <p>
                                每天更新
                            </p>
                          </div>

                          </a>
                      </li><li>
                          <a>
                          <img src="http://p2.music.126.net/5tgOCD4jiPKBGt7znJl-2g==/18822539557941307.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>云音乐电音榜</span>
                            <p>
                                每天更新
                            </p>
                          </div>

                          </a>
                      </li>
                  </ul>
              </div>
              <div className='rankright'>
                  <div className='title'>
                      <img src="http://p1.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg?param=150y150" alt=""/>
                      <div className='desc'>
                        <h2>云音乐飙升榜</h2>
                        <div className='date'>
                            <i className='iconfont'>&#xe650;</i>
                             最近更新：07月30日
                        </div>
                        <a href="" className='btn-first'>
                            <i className='iconfont '>&#xe645;</i>
                            播放
                        </a>
                        <a href="">
                            <i className='iconfont '>&#xe613;</i>
                            (3220952)
                        </a>
                        <a href="">
                            <i className='iconfont '>&#xe70d;</i>
                            (8857)
                        </a>
                        <a href="">
                            <i className='iconfont '>&#xe723;</i>
                            下载
                        </a>
                        <a href="">
                        <i className='iconfont '>&#xe60c;</i>
                        (190057)
                        </a>
                      </div>
                  </div>
                  <div className='songlist'>
                      <div className='u-title'>
                          <h3>歌曲列表</h3>
                          <span>100首歌</span>
                      </div>
                      <table>
                          <thead>
                            <th></th>
                            <th>标题 </th>
                            <th>时长</th>
                            <th>歌手</th>
                          </thead>
                          <tbody>
                            <tr className='list-color-1 '>
                                <td className='first'>1</td>
                                <td className='second'>
                                    <a>
                                        <img src="http://p2.music.126.net/HvB44MNINoLar8HFbRjIGQ==/109951165142435842.jpg?param=50y50&quality=100" alt=""/>
                                        <a class="iconfont"></a>
                                        <span>天外来物</span>
                                    </a>
                                </td>
                                <td className='third'>04:17</td>
                                <td className='fourth'>薛之谦</td>
                            </tr>
                            <tr className='list-color-2 '>
                                <td className='first'>2</td>
                                <td className='second'>
                                    <a>
                                        <img src="http://p2.music.126.net/JzsER44sOReoM6mR8XKnsw==/109951165182029540.jpg?param=50y50&quality=100" alt=""/>
                                        <a class="iconfont"></a>
                                        <span>麻雀</span>
                                    </a>
                                </td>
                                <td className='third'>04:12</td>
                                <td className='fourth'>李荣浩</td>
                            </tr>
                            <tr className='list-color-1 '>
                                <td className='first'>3</td>
                                <td className='second'>
                                    <a>
                                        <img
                                            src="http://p2.music.126.net/HvB44MNINoLar8HFbRjIGQ==/109951165142435842.jpg?param=50y50&quality=100"
                                            alt=""/>
                                        <a className="iconfont"></a>
                                        <span>你走</span>
                                    </a>
                                </td>
                                <td className='third'>04:17</td>
                                <td className='fourth'>橙子</td>
                            </tr>
                            <tr className='list-color-1 '>
                                <td className='first'>4</td>
                                <td className='second'>
                                    <a>
                                        <img
                                            src="http://p2.music.126.net/HvB44MNINoLar8HFbRjIGQ==/109951165142435842.jpg?param=50y50&quality=100"
                                            alt=""/>
                                        <a className="iconfont"></a>
                                        <span>天外来物</span>
                                    </a>
                                </td>
                                <td className='third'>04:17</td>
                                <td className='fourth'>薛之谦</td>
                            </tr>
                            <tr className='list-color-1 '>
                                <td className='first'>5</td>
                                <td className='second'>
                                    <a>
                                        <img
                                            src="http://p2.music.126.net/HvB44MNINoLar8HFbRjIGQ==/109951165142435842.jpg?param=50y50&quality=100"
                                            alt=""/>
                                        <a className="iconfont"></a>
                                        <span>天外来物</span>
                                    </a>
                                </td>
                                <td className='third'>04:17</td>
                                <td className='fourth'>薛之谦</td>
                            </tr>
                            <tr className='list-color-1 '>
                                <td className='first'>6</td>
                                <td className='second'>
                                    <a>
                                        <img
                                            src="http://p2.music.126.net/HvB44MNINoLar8HFbRjIGQ==/109951165142435842.jpg?param=50y50&quality=100"
                                            alt=""/>
                                        <a className="iconfont"></a>
                                        <span>天外来物</span>
                                    </a>
                                </td>
                                <td className='third'>04:17</td>
                                <td className='fourth'>薛之谦</td>
                            </tr>
                            <tr className='list-color-1 '>
                                <td className='first'>7</td>
                                <td className='second'>
                                    <a>
                                        <img
                                            src="http://p2.music.126.net/HvB44MNINoLar8HFbRjIGQ==/109951165142435842.jpg?param=50y50&quality=100"
                                            alt=""/>
                                        <a className="iconfont"></a>
                                        <span>天外来物</span>
                                    </a>
                                </td>
                                <td className='third'>04:17</td>
                                <td className='fourth'>薛之谦</td>
                            </tr>
                          </tbody>
                      </table>
                  </div>
                  <div className='comment'>
                    <div className='comment-title'>
                        <h3>评论</h3>
                        <span>共191069条评论</span>
                    </div>
                    <div className='comment-head'>
                        <img src='https://p2.music.126.net/gXGlysrRNf3WWnPkmrlmnw==/109951165168051838.jpg?param=50y50'/>
                        <div className='content'>
                            <textarea placeholder='评论'/><br/>
                            <a>评论</a>
                        </div>
                        
                    </div>
                    <div className='comment-body'>
                       <div className='title'>
                           <h3>精彩评论</h3>
                       </div>
                       <div className='list'>
                           <img src="https://p1.music.126.net/BxCSxny7i-NpEZRrAGMOmw==/109951165203889073.jpg?param=50y50" alt=""/>
                            <div className='content'>
                                <a className='id'>被学习压迫的ZQ</a>
                                <span> :冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲</span>
                                <div className='bottom'>
                                    <span>14:30</span>
                                    <i className='iconfont'>&#xe60d;</i>
                                    (7681)
                                    |&nbsp;
                                    <a>回复</a>
                                </div>
                            </div>
                       </div>
                       <div className='list'>
                           <img src="https://p1.music.126.net/BxCSxny7i-NpEZRrAGMOmw==/109951165203889073.jpg?param=50y50" alt=""/>
                            <div className='content'>
                                <a>被学习压迫的ZQ</a>
                                <span> :冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲冲</span>
                                <div className='bottom'>
                                    <span>14:30</span>
                                    <i className='iconfont'>&#xe60d;</i>
                                    (7869)
                                    |&nbsp;
                                    <a>回复</a>
                                </div>
                            </div>
                       </div>
                    </div>
                  </div>
              </div>
           </div>
          <Footer/>
        </div>
    )
}

export default RanList
