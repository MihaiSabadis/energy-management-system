import { useState } from 'react'
import {
    Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, MenuItem, Stack, TextField,
} from '@mui/material'
import { apiFetch } from '../api/client'

const emptyForm = { username: '', password: '', fullName: '', email: '', address: '', role: 'CLIENT' }

export default function UserFormDialog({ user, onClose, onSaved }) {
    const isEdit = Boolean(user)
    const [form, setForm] = useState(() =>
        user
            ? { ...emptyForm, username: user.username, fullName: user.fullName,
                email: user.email, address: user.address ?? '' }
            : emptyForm,
    )
    const [error, setError] = useState('')
    const [saving, setSaving] = useState(false)

    const update = (field) => (e) => setForm((f) => ({ ...f, [field]: e.target.value }))

    async function handleSubmit(e) {
        e.preventDefault()
        setError('')
        setSaving(true)
        try {
            const address = form.address.trim() || null
            if (isEdit) {
                await apiFetch(`/api/users/${user.id}`, {
                    method: 'PUT',
                    body: { username: form.username, fullName: form.fullName, email: form.email, address },
                })
            } else {
                await apiFetch('/api/auth/users', {
                    method: 'POST',
                    body: { ...form, address },
                })
            }
            onSaved()
        } catch (err) {
            setError(err.message)
        } finally {
            setSaving(false)
        }
    }

    return (
        <Dialog open onClose={onClose} fullWidth maxWidth="sm">
            <form onSubmit={handleSubmit}>
                <DialogTitle>{isEdit ? 'Edit user' : 'New user'}</DialogTitle>
                <DialogContent>
                    <Stack spacing={2} sx={{ pt: 1 }}>
                        <TextField label="Username" value={form.username} onChange={update('username')}
                                   required disabled={isEdit}
                                   helperText={isEdit ? 'Usernames cannot be changed' : ''} />
                        {!isEdit && (
                            <TextField label="Password" type="password" value={form.password}
                                       onChange={update('password')} required
                                       helperText="At least 6 characters" />
                        )}
                        <TextField label="Full name" value={form.fullName} onChange={update('fullName')} required />
                        <TextField label="Email" type="email" value={form.email} onChange={update('email')} required />
                        <TextField label="Address" value={form.address} onChange={update('address')} />
                        {!isEdit && (
                            <TextField select label="Role" value={form.role} onChange={update('role')}>
                                <MenuItem value="CLIENT">Client</MenuItem>
                                <MenuItem value="ADMIN">Admin</MenuItem>
                            </TextField>
                        )}
                        {error && <Alert severity="error">{error}</Alert>}
                    </Stack>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose}>Cancel</Button>
                    <Button type="submit" variant="contained" disabled={saving}>
                        {saving ? 'Saving…' : 'Save'}
                    </Button>
                </DialogActions>
            </form>
        </Dialog>
    )
}