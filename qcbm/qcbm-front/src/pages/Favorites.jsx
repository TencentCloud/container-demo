import React from 'react';
import FavoriteItem from '../components/FavoriteItem'
import AuthService from '../service/AuthService'

export default class Favorites extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            books: []
        };
    }

    componentDidMount() {
        let orderList = this;

        let currentUser = AuthService.getCurrentUser();

        var reqUri = "/api/favorites/query/" + currentUser.id;

        fetch(reqUri)
        .then(response => response.json())
        .then(function (data) {
            data ? orderList.setState({ books: data }) : orderList.setState({ books: [] });
        })
        .catch(error => {
            console.log("Favorites get favorites response", error);
        });
    }

    render(){
        return(
            // <div className = "row m-3 p-3" >
            <div className="container-fluid pt-3 text-left" style={{ maxWidth: "1320px", marginBottom: "10rem" }}>
                {
                    this.state.books.map((item, index) => {
                        return <FavoriteItem book={item} key={index} />
                    })
                }
            </div>
        );
    }
}