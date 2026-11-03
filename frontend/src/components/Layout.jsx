import { NavLink, Outlet, useNavigate } from 'react-router'
import { AppBar, Box, Button, Container, Toolbar, Typography } from '@mui/material'
import LogoutIcon from '@mui/icons-material/Logout'
import { useAuth } from '../auth/useAuth'

export default function Layout() {
    const { auth, logout } = useAuth()
    const navigate = useNavigate()

    const links = auth.role === 'ADMIN'
        ? [{ to: '/admin/users', label: 'Users' }, { to: '/admin/devices', label: 'Devices' }]
        : [{ to: '/my-devices', label: 'My devices' }]

    function handleLogout() {
        logout()
        navigate('/login', { replace: true })
    }

    return (
        <>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" sx={{ mr: 4 }}>Energy Management</Typography>
                    <Box sx={{ flexGrow: 1, display: 'flex', gap: 1 }}>
                        {links.map((l) => (
                            <Button key={l.to} component={NavLink} to={l.to} color="inherit"
                                    sx={{ '&.active': { bgcolor: 'rgba(255,255,255,0.15)' } }}>
                                {l.label}
                            </Button>
                        ))}
                    </Box>
                    <Typography sx={{ mr: 2 }}>{auth.username} ({auth.role})</Typography>
                    <Button color="inherit" startIcon={<LogoutIcon />} onClick={handleLogout}>
                        Log out
                    </Button>
                </Toolbar>
            </AppBar>
            <Container sx={{ py: 3 }}>
                <Outlet />
            </Container>
        </>
    )
}