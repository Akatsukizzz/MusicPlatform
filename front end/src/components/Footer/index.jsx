import React from "react";
import './style.css'
import { Row, Col } from 'antd';
import {ChromeOutlined} from "@ant-design/icons";

const Footer = () =>{
    return(
        <Row className="footer">
            <Col span={15} offset={4}>
                <div className="copy-right">
                    <p className="service-link">
                        <a href="#">服务条款</a>
                        <span>  |  </span>
                        <a href="#">隐私政策</a>
                        <span>  |  </span>
                        <a href="#">儿童隐私政策</a>
                        <span>  |  </span>
                        <a href="#">版权投诉指引</a>
                        <span>  |  </span>
                        <a href="#">意见反馈</a>
                    </p>
                    <p>云音乐平台版权所有©2020-2020</p>
                    <p>违法和不良信息举报电话：21345</p>
                    <p>测试性实习网站</p>
                </div>
                <div className="admire">
                            <span>
                                <ChromeOutlined />
                                <em>用户中心</em>
                            </span>
                    <span>
                                <ChromeOutlined />
                                <em>赞赏</em>
                            </span>
                    <span>
                                <ChromeOutlined />
                                <em>独立音乐人</em>
                            </span>
                </div>
            </Col>
        </Row>
    )
}

export default Footer
