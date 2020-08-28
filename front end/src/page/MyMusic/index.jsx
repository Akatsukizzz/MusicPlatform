import React from "react";
import './style.css'

const MyMusic = () => {
    return(
        <div>
           <div className='rankwrapper'>
              <div className='rankleft'>
                  <h2>创建的歌单</h2>
                  <ul className='rank-special'>
                      <li>
                          <a>
                          <img src="https://p2.music.126.net/IqmOYwodp1x18EmrZCzCUg==/109951164742593680.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>我喜欢的音乐</span>
                            <p>
                                357首
                            </p>
                          </div>
                          </a>
                      </li>
                      <li>
                          <a>
                          <img src="https://p2.music.126.net/IqmOYwodp1x18EmrZCzCUg==/109951164742593680.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>我喜欢的音乐</span>
                            <p>
                                357首
                            </p>
                          </div>
                          </a>
                      </li><li>
                          <a>
                          <img src="https://p2.music.126.net/IqmOYwodp1x18EmrZCzCUg==/109951164742593680.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>我喜欢的音乐</span>
                            <p>
                                357首
                            </p>
                          </div>
                          </a>
                      </li>
                  </ul>
                  <h2>收藏的歌单</h2>
                  <ul className='rank-media'>
                  <li>
                          <a>
                          <img src="https://p1.music.126.net/uwdLRBUVqy-rDnYAWkM4JQ==/109951163735072939.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>如果你也喜欢陈奕迅</span>
                            <p>
                                72首
                            </p>
                          </div>

                          </a>
                      </li>
                      <li>
                          <a>
                          <img src="https://p1.music.126.net/uwdLRBUVqy-rDnYAWkM4JQ==/109951163735072939.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>如果你也喜欢陈奕迅</span>
                            <p>
                                72首
                            </p>
                          </div>

                          </a>
                      </li>  <li>
                          <a>
                          <img src="https://p1.music.126.net/uwdLRBUVqy-rDnYAWkM4JQ==/109951163735072939.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>如果你也喜欢陈奕迅</span>
                            <p>
                                72首
                            </p>
                          </div>

                          </a>
                      </li>
                  </ul>
              </div>
              <div className='rankright'>
                  <div className='title'>
                      <img src="https://p2.music.126.net/uwdLRBUVqy-rDnYAWkM4JQ==/109951163735072939.jpg?param=200y200" alt=""/>
                      <div className='desc'>
                        <h2>我喜欢的音乐</h2>
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
                            <th>1</th>
                            <th>标题 </th>
                            <th>时长</th>
                            <th>歌手</th>
                          </thead>
                          <tbody>
                            <tr className='list-color-1' id='list-height'>
                                <td className='first'>1</td>
                                <td className='second'>
                                    <a>
                                        <a class="iconfont"></a>
                                        <span>富士山下</span>
                                    </a>
                                </td>
                                <td className='third'>04:19</td>
                                <td className='fourth'>陈奕迅</td>
                            </tr>
                            <tr className='list-color-2 '  id='list-height'>
                                <td className='first'>2</td>
                                <td className='second'>
                                    <a>
                                         <a class="iconfont"></a>
                                        <span>阿猫阿狗</span>
                                    </a>
                                </td>
                                <td className='third'>03:49</td>
                                <td className='fourth'>陈奕迅</td>
                            </tr>
                          </tbody>
                      </table>
                  </div>
              </div>
           </div>
        </div>
    )
}

export default MyMusic
