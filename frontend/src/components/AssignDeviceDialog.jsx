import { useState } from 'react'
import {
    Alert, Autocomplete, Button, Dialog, DialogActions, DialogContent, DialogTitle, Stack, TextField,
} from '@mui/material'
import { apiFetch } from '../api/client'

export default function AssignDeviceDialog({ device, users, onClose, onSaved }) {
    const [selected, setSelected] = useState(
        () => users.find((u) => u.id === device.userId) ?? null,
    )
    const [error, setError] = useState('')
    const [saving, setSaving] = useState(false)

    async function handleSave() {
        setError('')
        setSaving(true)
        try {
            if (selected) {
                await apiFetch(`/api/devices/${device.id}/user/${selected.id}`, { method: 'PUT' })
            } else {
                await apiFetch(`/api/devices/${device.id}/user`, { method: 'DELETE' })
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
            <DialogTitle>Assign "{device.name}"</DialogTitle>
            <DialogContent>
                <Stack spacing={2} sx={{ pt: 1 }}>
                    <Autocomplete
                        options={users}
                        value={selected}
                        onChange={(_, value) => setSelected(value)}
                        getOptionLabel={(u) => `${u.username} (${u.fullName})`}
                        isOptionEqualToValue={(option, value) => option.id === value.id}
                        renderInput={(params) => (
                            <TextField {...params} label="User" helperText="Clear the field to unassign the device" />
                        )}
                    />
                    {error && <Alert severity="error">{error}</Alert>}
                </Stack>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button variant="contained" onClick={handleSave} disabled={saving}>
                    {saving ? 'Saving…' : 'Save'}
                </Button>
            </DialogActions>
        </Dialog>
    )
}