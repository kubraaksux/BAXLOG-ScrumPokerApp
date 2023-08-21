import React, { useEffect } from "react";
import { Button, Container, Row } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import Header from "../components/Header";

function PokerPage() {

    return (
        <div className="main">
            <Header />
            <Container>
                <Row>
                    <div className="intro-text">
                        <div>
                            <h1 className="title">WELCOME TO BAX-LOG</h1>
                        </div>
                    </div>
                </Row>
            </Container>
        </div>
    );
}

export default PokerPage;
