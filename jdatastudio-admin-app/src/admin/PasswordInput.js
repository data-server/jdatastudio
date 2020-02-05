import React, {Component} from "react";
import {TextInput} from "react-admin";
import AutorenewIcon from "@material-ui/icons/Autorenew";
import IconButton from "@material-ui/core/IconButton";

const generatePassword = () => {
    var length = 8,
        charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",
        retVal = "";
    for (var i = 0, n = charset.length; i < length; ++i) {
        retVal += charset.charAt(Math.floor(Math.random() * n));
    }
    return retVal;
}

class PasswordInput extends Component {

    render() {
        const {input: { onChange}} = this.props
        return (
            <span>
                <TextInput source="password" label="密码"/>
                        &nbsp;
                <IconButton label="Reset" onClick={() => {onChange(generatePassword()); }}>
                    <AutorenewIcon />
                </IconButton>
            </span>
        )
    }
}

export default PasswordInput;