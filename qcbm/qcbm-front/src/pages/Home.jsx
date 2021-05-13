import React from 'react';
import BookItem from '../components/BookItem'

export default class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loggedIn: false,
            books: []
        };
    }

    componentDidMount() {
        let compHome = this;

        fetch("/api/book/all")
        .then(response => response.json())
        .then(function (data) {
            // console.log(data);        
            compHome.setState({books : data})
        });
    }

    render() {

        return (
            <div className="row m-3 p-3" >
                { 
                    this.state.books.map((item, index) =>{
                        return <BookItem book={item} key={index} />
                    })
                }
            </div>
        );
    }
};
