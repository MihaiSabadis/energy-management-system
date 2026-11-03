import { useCallback, useEffect, useMemo, useState } from 'react'
import { Alert, Box, Button, Stack, Typography } from '@mui/material'
import { DataGrid, GridActionsCellItem } from '@mui/x-data-grid'
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import { apiFetch } from '../../api/client'
import { useAuth } from '../../auth/useAuth'
import ConfirmDialog from '../../components/ConfirmDialog'
import UserFormDialog from '../../components/UserFormDialog'

export default function AdminUsersPage() {
    const { auth } = useAuth()
    const [users, setUsers] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState('')
    const [dialog, setDialog] = useState(null)        // null = closed, { user } = open
    const [toDelete, setToDelete] = useState(null)
    const [deleting, setDeleting] = useState(false)

    const loadUsers = useCallback(async () => {
        try {
            setUsers(await apiFetch('/api/users'))
            setError('')
        } catch (err) {
            setError(err.message)
        } finally {
            setLoading(false)
        }
    }, [])

    useEffect(() => {
        loadUsers()
    }, [loadUsers])

    async function handleDelete() {
        setDeleting(true)
        try {
            await apiFetch(`/api/users/${toDelete.id}`, { method: 'DELETE' })
            await loadUsers()
        } catch (err) {
            setError(err.message)
        } finally {
            setDeleting(false)
            setToDelete(null)
        }
    }

    const columns = useMemo(() => [
        { field: 'username', headerName: 'Username', flex: 1 },
        { field: 'fullName', headerName: 'Full name', flex: 1.2 },
        { field: 'email', headerName: 'Email', flex: 1.5 },
        { field: 'address', headerName: 'Address', flex: 1 },
        {
            field: 'actions', type: 'actions', headerName: 'Actions', width: 110,
            getActions: ({ row }) => [
                <GridActionsCellItem key="edit" icon={<EditIcon />} label="Edit"
                                     onClick={() => setDialog({ user: row })} />,
                <GridActionsCellItem key="delete" icon={<DeleteIcon />} label="Delete"
                                     disabled={row.id === auth.userId}
                                     onClick={() => setToDelete(row)} />,
            ],
        },
    ], [auth.userId])

    return (
        <Box>
            <Stack direction="row" sx={{ justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                <Typography variant="h5">Users</Typography>
                <Button variant="contained" startIcon={<AddIcon />} onClick={() => setDialog({ user: null })}>
                    New user
                </Button>
            </Stack>

            {error && <Alert severity="error" sx={{ mb: 2 }} onClose={() => setError('')}>{error}</Alert>}

            <Box sx={{ height: 520 }}>
                <DataGrid rows={users} columns={columns} loading={loading} disableRowSelectionOnClick
                          initialState={{ pagination: { paginationModel: { pageSize: 10 } } }}
                          pageSizeOptions={[10, 25, 50]} />
            </Box>

            {dialog && (
                <UserFormDialog
                    user={dialog.user}
                    onClose={() => setDialog(null)}
                    onSaved={() => { setDialog(null); loadUsers() }}
                />
            )}

            {toDelete && (
                <ConfirmDialog
                    title="Delete user"
                    message={`Delete "${toDelete.username}"? Their credentials are removed and their devices become unassigned.`}
                    loading={deleting}
                    onCancel={() => setToDelete(null)}
                    onConfirm={handleDelete}
                />
            )}
        </Box>
    )
}