import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import Card from '@material-ui/core/Card';
import Avatar from '@material-ui/core/Avatar';
import { withStyles } from '@material-ui/core/styles';
import {Notification} from 'react-admin';
import DefaultLoginForm from './LoginForm';
import { red } from '@material-ui/core/colors';
const styles = theme => ({
    main: {
        display: 'flex',
        flexDirection: 'column',
        minHeight: '100vh',
        alignItems: 'center',
        justifyContent: 'center',
        // background: 'url(https://source.unsplash.com/random/1600x900)',
        backgroundColor: 'red',
        backgroundSize: 'cover',
    },
    card: {
        minWidth: 300,
    },
    avatar: {
        margin: '1em',
        display: 'flex',
        justifyContent: 'center',
    },
    icon: {
        backgroundColor: red[500],
    },
});

const sanitizeRestProps = ({
    classes,
    className,
    location,
    title,
    array,
    theme,
    staticContext,
    ...rest
}) => rest;

/**
 * A standalone login page, to serve as authentication gate to the admin
 *
 * Expects the user to enter a login and a password, which will be checked
 * by the `authProvider` using the AUTH_LOGIN verb. Redirects to the root page
 * (/) upon success, otherwise displays an authentication error message.
 *
 * Copy and adapt this component to implement your own login logic
 * (e.g. to authenticate via email or facebook or anything else).
 *
 * @example
 *     import MyLoginPage from './MyLoginPage';
 *     const App = () => (
 *         <Admin loginPage={MyLoginPage} authProvider={authProvider}>
 *             ...
 *        </Admin>
 *     );
 */
const Login = ({ classes, className, loginForm, ...rest }) => (
    <div
        className={classnames(classes.main, className)}
        {...sanitizeRestProps(rest)}
    >
        <Card className={classes.card}>
            <div className={classes.avatar}>
                <Avatar className={classes.icon}>
                </Avatar>
            </div>
            {loginForm}
        </Card>
        <Notification />
    </div>
);

Login.propTypes = {
    className: PropTypes.string,
    authProvider: PropTypes.func,
    classes: PropTypes.object,
    input: PropTypes.object,
    meta: PropTypes.object,
    previousRoute: PropTypes.string,
    loginForm: PropTypes.element,
};

Login.defaultProps = {
    loginForm: <DefaultLoginForm />,
};

export default withStyles(styles)(Login);
