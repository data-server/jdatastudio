import React, {Component} from "react";
import PropTypes from "prop-types";
import {connect} from "react-redux";
import IconButton from "@material-ui/core/IconButton";
import {showNotification as showNotificationAction} from "react-admin";
import {push as pushAction} from "react-router-redux";
import {url} from "../constants";
import {httpClient} from "../authProvider";
import FalseIcon from "@material-ui/icons/Clear";
import TrueIcon from "@material-ui/icons/Done";


class CheckButton extends Component {
    constructor(props) {
        super(props);
        this.state = {status: props.record[props.source]};
    }

    handleCheck = () => {
        const {record, showNotification, roleId,source} = this.props;
        const updatedRecord = record;
        let checkStatus = this.state.status;
        updatedRecord.id = record.id;
        updatedRecord[source] = !checkStatus;
        httpClient(url + `/roles/${roleId}/editPermission`, {method: 'PUT', body: JSON.stringify(updatedRecord)})
            .then(() => {
                showNotification('操作成功');
                this.setState({
                    status: !checkStatus
                });
                pushAction('/permissions');
            })
            .catch((e) => {
                console.error(e);
                showNotification('操作失败', 'warning')
            });
    }

    render() {
        return (this.state.status ?
                <IconButton onClick={this.handleCheck}>
                    <TrueIcon />
                </IconButton> :
                <IconButton onClick={this.handleCheck}>
                    <FalseIcon />
                </IconButton>
        );
    }
}

CheckButton.propTypes = {
    push: PropTypes.func,
    record: PropTypes.object,
    source: PropTypes.string,
    showNotification: PropTypes.func,
};

export default connect(null, {
    showNotification: showNotificationAction,
    push: pushAction,
})(CheckButton);