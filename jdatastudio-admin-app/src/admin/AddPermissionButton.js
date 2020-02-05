import React from "react";
import {Link} from "react-router-dom";
import ChatBubbleIcon from "@material-ui/icons/ChatBubble";
import {withStyles} from "@material-ui/core/styles";
import {Button} from "react-admin";

const styles = {
    button: {
        marginTop: '1em'
    }
};

const AddPermissionButton = ({classes, record}) => (
    <Button
        className={classes.button}
        variant="raised"
        component={Link}
        to={`/resources/create?role_id=${record.id}`}
        label="添加授权"
        title="添加授权"
    >
        <ChatBubbleIcon />
    </Button>
);

export default withStyles(styles)(AddPermissionButton);
