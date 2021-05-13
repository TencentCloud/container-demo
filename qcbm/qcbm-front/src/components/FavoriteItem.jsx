import React from 'react'
import { withRouter } from 'react-router-dom';
import AuthService from '../service/AuthService'

class FavoriteItem extends React.Component {

    buyBook = () => {

        const tmp = this;

        let currentUser  = AuthService.getCurrentUser();
        // console.log(currentUser);
        if (!currentUser || !currentUser.id) {
            window.location = "/signin";
            return
        }

        let url = "/api/book/purchase?userId=" + currentUser.id + "&isbn=" + tmp.props.book.isbn;
        console.log("favorites url = ", url);

        fetch(url)
        .then(function (response) {
            // console.log(response);
            tmp.props.history.push("/bookshelf");
        })
        .catch(error => {
            alert(error)
        })
    };

    deleteFavorite = ()=>{

        let currentUser  = AuthService.getCurrentUser();
        if (!currentUser || !currentUser.id) {
            window.location = "/signin";
            return
        }

        fetch("/api/favorites/delete?userId=" + currentUser.id + "&isbn=" + this.props.book.isbn)
        .then(response => {
            window.location = "/facorites"
        })
    };

    render(){
        return(
            <div className="row mt-2 mb-2 bookItemBox scaleImg bg-light">
                <div className="col-3 bookImg">
                    <img style={{ width: "19rem", height: "17rem" }} src={"./books/" + this.props.book.isbn + "/cover.jpeg"} alt=""/>
                </div>
                <div className="col-9 bookDesc">
                    <div className="title">{ this.props.book.title}</div>
                    <div className="price">￥{this.props.book.price}</div>
                    <span style={{ color: "orange"}}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-star-fill" viewBox="0 0 16 16">
                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z" />
                        </svg>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-star-fill" viewBox="0 0 16 16">
                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z" />
                        </svg>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-star-fill" viewBox="0 0 16 16">
                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.283.95l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z" />
                        </svg>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-star-half" viewBox="0 0 16 16">
                            <path d="M5.354 5.119L7.538.792A.516.516 0 0 1 8 .5c.183 0 .366.097.465.292l2.184 4.327 4.898.696A.537.537 0 0 1 16 6.32a.55.55 0 0 1-.17.445l-3.523 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256a.519.519 0 0 1-.146.05c-.341.06-.668-.254-.6-.642l.83-4.73L.173 6.765a.55.55 0 0 1-.171-.403.59.59 0 0 1 .084-.302.513.513 0 0 1 .37-.245l4.898-.696zM8 12.027c.08 0 .16.018.232.056l3.686 1.894-.694-3.957a.564.564 0 0 1 .163-.505l2.906-2.77-4.052-.576a.525.525 0 0 1-.393-.288L8.002 2.223 8 2.226v9.8z" />
                        </svg>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-star" viewBox="0 0 16 16">
                            <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.523-3.356c.329-.314.158-.888-.283-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767l-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288l1.847-3.658 1.846 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.564.564 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z" />
                        </svg>
                    </span>
                    <div className="author">{this.props.book.author}</div>
                    <div className="other-info">
                        <span>{this.props.book.press}</span>&nbsp;/&nbsp;<span>{this.props.book.pubDate}</span>
                    </div>
                    <div className="other-info">
                        <span>ISBN: {this.props.book.isbn}</span>
                    </div>
                    <div className="abstract">{this.props.book.briefIntro}</div>
                    <div className="text-right mb-3 mr-3">
                        <button onClick={this.buyBook} type="button" className="btn btn-danger mr-1" style={{ width: "5rem" }}>购买</button>
                        <button onClick={this.deleteFavorite} type="button" className="btn btn-light mr-1" style={{ width: "5rem" }}>删除</button>
                    </div>
                </div>
            </div>
        )
    }
};

export default withRouter(FavoriteItem);