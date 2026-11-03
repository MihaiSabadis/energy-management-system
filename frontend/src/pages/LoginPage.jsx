import { useState } from 'react'
import { Navigate, useNavigate } from 'react-router'
import { useAuth } from '../auth/useAuth'

import { Alert, Box, Button, Paper, TextField, Typography } from '@mui/material'

export default function LoginPage() {
    const { auth, login } = useAuth()
    const navigate = useNavigate()
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(false)

    if (auth) return <Navigate to="/" replace />

    async function handleSubmit(e) {
        e.preventDefault()
        setError('')
        setLoading(true)
        try {
            const data = await login(username, password)
            navigate(data.role === 'ADMIN' ? '/admin/users' : '/my-devices', { replace: true })
        } catch (err) {
            setError(err.message)
        } finally {
            setLoading(false)
        }
    }

    return (
        <Box sx={{ minHeight: '100vh', display: 'grid', placeItems: 'center', bgcolor: 'grey.100' }}>
            <Paper component="form" onSubmit={handleSubmit}
                   sx={{ p: 4, width: 360, display: 'grid', gap: 2 }}>
                <Typography variant="h5">Log in</Typography>
                <TextField label="Username" value={username} required autoFocus
                           onChange={(e) => setUsername(e.target.value)} />
                <TextField label="Password" type="password" value={password} required
                           onChange={(e) => setPassword(e.target.value)} />
                {error && <Alert severity="error">{error}</Alert>}
                <Button type="submit" variant="contained" disabled={loading}>
                    {loading ? 'Logging in…' : 'Log in'}
                </Button>
            </Paper>
        </Box>
    )
}