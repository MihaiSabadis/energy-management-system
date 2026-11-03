import { useEffect, useState } from 'react'
import { apiFetch } from '../../api/client'
import { useAuth } from '../../auth/useAuth'

import { Alert, Box, Typography } from '@mui/material'
import { DataGrid } from '@mui/x-data-grid'


// columns is outside the component so it does not recreate with every render

const columns = [
    { field: 'name', headerName: 'Name', flex: 1 },
    { field: 'description', headerName: 'Description', flex: 1.5 },
    { field: 'maxConsumption', headerName: 'Max consumption (kWh)', type: 'number', flex: 1 },
]

export default function MyDevicesPage() {
    const { auth } = useAuth()
    const [devices, setDevices] = useState([])
    const [error, setError] = useState('')
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        let cancelled = false
        apiFetch(`/api/devices/user/${auth.userId}`)
            .then((data) => { if (!cancelled) setDevices(data) })
            .catch((err) => { if (!cancelled) setError(err.message) })
            .finally(() => { if (!cancelled) setLoading(false) })
        return () => { cancelled = true }
    }, [auth.userId])

    return (
        <Box>
            <Typography variant="h5" sx={{ mb: 2 }}>My devices</Typography>
            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
            <Box sx={{ height: 450 }}>
                <DataGrid rows={devices} columns={columns} loading={loading}
                          disableRowSelectionOnClick
                          localeText={{ noRowsLabel: 'No devices are assigned to you yet.' }} />
            </Box>
        </Box>
    )
}