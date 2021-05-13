import React from 'react';
import AuthService from '../service/AuthService'

export default class Bookshelf extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            orders: []
        };
    }

    componentDidMount() {
        let orderList = this;
        let currentUser = AuthService.getCurrentUser();
        let bookShelfUrl = "api/order/" + currentUser.id;

        fetch(bookShelfUrl)
        .then(response => response.json())
        .then(function (data) {
            // console.log(data);
            data ? orderList.setState({ orders: data }) : orderList.setState({ orders: [] });
        })
        .catch(error => {
            console.log("Bookshelf get order response", error);
        });
    }

    render() {
        return (
            <div className="ml-5 mt-3 mr-5 mb-3">
                <table className="table" style={{fontSize: "18px"}}>
                    <thead className="thead-light">
                        <tr>
                            <th scope="col">订单号</th>
                            <th scope="col">封面</th>
                            <th scope="col">图书</th>
                            <th scope="col">作者</th>
                            <th scope="col">ISBN</th>
                            <th scope="col">购买日期</th>
                            <th scope="col">Operate</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.orders.map((item) => {
                                let imgSrc ="./books/" + item.isbn + "/cover.jpeg";
                                return <tr key={item.orderId}>
                                    <th style={{ verticalAlign: "middle" }}>{item.orderId}</th>
                                    <th scope="row" style={{ verticalAlign: "middle" }}>
                                        <img style={{ width: "5rem", height: "5rem" }} src={imgSrc} alt="" />
                                    </th>
                                    <td style={{ verticalAlign: "middle" }}>{item.bookTitle}</td>
                                    <td style={{ verticalAlign: "middle" }}>{item.author}</td>
                                    <td style={{ verticalAlign: "middle" }}>{item.isbn}</td>
                                    <td style={{ verticalAlign: "middle" }}>{item.purchaseDate}</td>
                                    <td style={{ verticalAlign: "middle" }}>
                                        <button type="button" className="btn btn-info btn-sm">阅读</button>
                                    </td>
                                </tr>
                            })
                        }
                    </tbody>
                </table>
            </div>
        );
    }
};