import React from 'react'
import './style.css'
import { Row, Col, Input, Modal, Button, Checkbox, Form } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { BrowserRouter as Router, Switch, NavLink, Redirect} from "react-router-dom";
import { renderRoutes } from 'react-router-config';
import routes from "../../route/routes";

const { Search } = Input;
    const onFinish = values => {
        console.log('Received values of form: ', values);
    }
// const son = () =>{
    // console.log(routes);
    // console.log(renderRoutes(routes));
// }
// son();

class TopNav extends React.Component {
    state = {
        loading: false,
        visible: false,
        regvisible:false
    };


    showModal = () => {
        this.setState({
            visible: true,
        });
    };

    ShowRegisterModal = () => {
      this.setState( {
          visible: false,
          regvisible: true,
      })
    };

    handleOk = () => {
        this.setState({ loading: true });
        setTimeout(() => {
            this.setState({ loading: false, visible: false });
        }, 3000);
    };

    handleCancel = () => {
        this.setState({ visible: false });
    };

    render() {
        return(
            <Router>
                <Row align="middle" className="top-nav">
                    <Col span={16} offset={5} className="left-nav">
                        <ul>
                            <li>
                                <NavLink activeClassName="selected" to="/home">发现音乐</NavLink>
                            </li>
                            <li>
                                <NavLink activeClassName="selected" to="/mymusic">我的音乐</NavLink>
                            </li>
                            <li>
                                <NavLink activeClassName="selected" to="/friend">朋友</NavLink>
                            </li>
                            <li>
                                <NavLink activeClassName="selected" to="/shop">商城</NavLink>
                            </li>
                            <li>
                                <NavLink activeClassName="selected" to="/musician">音乐人</NavLink>
                            </li>
                            <li>
                                <NavLink activeClassName="selected" to="/download">下载客户端</NavLink>
                            </li>
                        </ul>
                    </Col>

                    <Col span={8} offset={16}>
                        <Search
                            className="search"
                            placeholder="搜索音乐/用户"
                            onSearch={value => console.log(value)}
                            style={{ width: 150 }}
                        />
                    <span className="login" onClick={this.showModal}>
                        登陆
                    </span>
                        <Modal
                            visible={this.state.visible}
                            title="登陆"
                            onOk={this.handleOk}
                            onCancel={this.handleCancel}
                            footer={null}
                            className="login-modal"
                            centered={true}
                        >
                            <Form
                                name="normal_login"
                                className="login-form"
                                initialValues={{ remember: true }}
                                onFinish={onFinish}
                            >
                                <Form.Item
                                    name="username"
                                    className="user-name"
                                    rules={[{ required: true, message: '请输入账号!' }]}
                                >
                                    <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="请输入手机号" />
                                </Form.Item>
                                <Form.Item
                                    name="password"
                                    className="password"
                                    rules={[{ required: true, message: '请输入密码!' }]}
                                >
                                    <Input
                                        prefix={<LockOutlined className="site-form-item-icon" />}
                                        type="password"
                                        placeholder="请输入密码"
                                    />
                                </Form.Item>
                                <Form.Item
                                    className="forget-pwd"
                                >
                                    <Form.Item name="remember" valuePropName="checked" style={{float:"left"}}>
                                        <Checkbox>记住我</Checkbox>
                                    </Form.Item>

                                    <a className="login-form-forgot" href="" style={{float:"right"}}>
                                        忘记密码
                                    </a>
                                </Form.Item>

                                <Form.Item>
                                    <Button type="primary" htmlType="submit" className="login-form-button" style={{display:"block",width:"100%"}}>
                                        登陆
                                    </Button>
                                    <a href="javascript:;" onClick={this.ShowRegisterModal}>注册用户</a>
                                </Form.Item>
                            </Form>
                        </Modal>
                    </Col>
                </Row>

                {/* 加载路由组件 */}
                <Switch>
                    <Redirect exact path="/" to="home" />
                    { renderRoutes(routes) }
                </Switch>
            </Router>
        )
    }
}

export default TopNav
