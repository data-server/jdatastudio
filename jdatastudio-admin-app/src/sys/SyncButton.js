import React, {Component} from "react";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import Button from "@material-ui/core/Button";
import NotificationSync from "@material-ui/icons/Sync";
import {showNotification as showNotificationAction} from "react-admin";
import {push as pushAction} from "react-router-redux";
import {url} from "../constants";
import {httpClient} from "./../authProvider";
class SyncButton extends Component {
    handleSync = () => {
        const {record, showNotification} = this.props;
        httpClient(url + `/_datasource/sync/${record.id}`, {method: 'GET'})
            .then(() => {
                showNotification('同步成功!');
            })
            .catch((e) => {
                console.error(e);
                showNotification(e.message, 'warning')
            });
    }

    render() {
        return (
            <Button onClick={this.handleSync}>
                <NotificationSync/>
                同步表结构
            </Button>
        );
    }
}

SyncButton.propTypes = {
    push: PropTypes.func,
    record: PropTypes.object,
    showNotification: PropTypes.func,
};

export default connect(null, {
    showNotification: showNotificationAction,
    push: pushAction,
})(SyncButton);