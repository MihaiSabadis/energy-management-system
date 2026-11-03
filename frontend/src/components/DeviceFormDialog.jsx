import { useState } from 'react'
import {
    Alert, Button, Dialog, DialogActions, DialogContent, DialogTitle, Stack, TextField,
} from '@mui/material'
import { apiFetch } from '../api/client'

const emptyForm = { name: '', description: '',  maxConsumption: '' }

export default function DeviceFormDialog({ device, onClose, onSaved }) {
    const isEdit = Boolean(device)
    const [form, setForm] = useState(() =>
        device
            ? {
                name: device.name,
                description: device.description ?? '',
                maxConsumption: String(device.maxConsumption),
            }
            : emptyForm,
    )
    const [error, setError] = useState('')
    const [saving, setSaving] = useState(false)

    const update = (field) => (e) => setForm((f) => ({ ...f, [field]: e.target.value }))

    async function handleSubmit(e) {
        e.preventDefault()
        setError('')
        setSaving(true)
        const body = {
            name: form.name.trim(),
            description: form.description.trim() || null,
            maxConsumption: Number(form.maxConsumption),
        }
        try {
            if (isEdit) {
                await apiFetch(`/api/devices/${device.id}`, { method: 'PUT', body })
            } else {
                await apiFetch('/api/devices', { method: 'POST', body })
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
                <DialogTitle>{isEdit ? 'Edit device' : 'New device'}</DialogTitle>
                <DialogContent>
                    <Stack spacing={2} sx={{ pt: 1 }}>
                        <TextField label="Name" value={form.name} onChange={update('name')} required />
                        <TextField label="Description" value={form.description} onChange={update('description')}
                                   multiline minRows={2} />
                        <TextField label="Max consumption (kWh)" type="number" value={form.maxConsumption}
                                   onChange={update('maxConsumption')} required
                                   slotProps={{ htmlInput: { min: 0.01, step: 0.01 } }} />
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