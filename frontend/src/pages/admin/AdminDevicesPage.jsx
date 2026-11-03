import { useCallback, useEffect, useMemo, useState } from 'react'
import { Alert, Box, Button, Stack, Typography } from '@mui/material'
import { DataGrid, GridActionsCellItem } from '@mui/x-data-grid'
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import PersonAddIcon from '@mui/icons-material/PersonAdd'
import { apiFetch } from '../../api/client'
import ConfirmDialog from '../../components/ConfirmDialog'
import DeviceFormDialog from '../../components/DeviceFormDialog'
import AssignDeviceDialog from '../../components/AssignDeviceDialog'

export default function AdminDevicesPage() {
    const [devices, setDevices] = useState([])
    const [users, setUsers] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState('')
    const [formDialog, setFormDialog] = useState(null)     // null = closed, { device } = open
    const [toAssign, setToAssign] = useState(null)
    const [toDelete, setToDelete] = useState(null)
    const [deleting, setDeleting] = useState(false)

    const loadData = useCallback(async () => {
        try {
            const [devicesData, usersData] = await Promise.all([
                apiFetch('/api/devices'),
                apiFetch('/api/users'),
            ])
            setDevices(devicesData)
            setUsers(usersData)
            setError('')
        } catch (err) {
            setError(err.message)
        } finally {
            setLoading(false)
        }
    }, [])

    useEffect(() => {
        loadData()
    }, [loadData])

    const usernameById = useMemo(
        () => new Map(users.map((u) => [u.id, u.username])),
        [users],
    )

    async function handleDelete() {
        setDeleting(true)
        try {
            await apiFetch(`/api/devices/${toDelete.id}`, { method: 'DELETE' })
            await loadData()
        } catch (err) {
            setError(err.message)
        } finally {
            setDeleting(false)
            setToDelete(null)
        }
    }

    const columns = useMemo(() => [
        { field: 'name', headerName: 'Name', flex: 1 },
        { field: 'description', headerName: 'Description', flex: 1.5 },
        { field: 'maxConsumption', headerName: 'Max consumption (kWh)', type: 'number', flex: 1 },
        {
            field: 'userId', headerName: 'Assigned to', flex: 1,
            valueGetter: (value) => (value ? usernameById.get(value) ?? 'Unknown user' : 'Unassigned'),
        },
        {
            field: 'actions', type: 'actions', headerName: 'Actions', width: 140,
            getActions: ({ row }) => [
                <GridActionsCellItem key="assign" icon={<PersonAddIcon />} label="Assign"
                                     onClick={() => setToAssign(row)} />,
                <GridActionsCellItem key="edit" icon={<EditIcon />} label="Edit"
                                     onClick={() => setFormDialog({ device: row })} />,
                <GridActionsCellItem key="delete" icon={<DeleteIcon />} label="Delete"
                                     onClick={() => setToDelete(row)} />,
            ],
        },
    ], [usernameById])

    return (
        <Box>
            <Stack direction="row" sx={{ justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                <Typography variant="h5">Devices</Typography>
                <Button variant="contained" startIcon={<AddIcon />} onClick={() => setFormDialog({ device: null })}>
                    New device
                </Button>
            </Stack>

            {error && <Alert severity="error" sx={{ mb: 2 }} onClose={() => setError('')}>{error}</Alert>}

            <Box sx={{ height: 520 }}>
                <DataGrid rows={devices} columns={columns} loading={loading} disableRowSelectionOnClick
                          initialState={{ pagination: { paginationModel: { pageSize: 10 } } }}
                          pageSizeOptions={[10, 25, 50]} />
            </Box>

            {formDialog && (
                <DeviceFormDialog
                    device={formDialog.device}
                    onClose={() => setFormDialog(null)}
                    onSaved={() => { setFormDialog(null); loadData() }}
                />
            )}

            {toAssign && (
                <AssignDeviceDialog
                    device={toAssign}
                    users={users}
                    onClose={() => setToAssign(null)}
                    onSaved={() => { setToAssign(null); loadData() }}
                />
            )}

            {toDelete && (
                <ConfirmDialog
                    title="Delete device"
                    message={`Delete "${toDelete.name}"? This cannot be undone.`}
                    loading={deleting}
                    onCancel={() => setToDelete(null)}
                    onConfirm={handleDelete}
                />
            )}
        </Box>
    )
}