import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material'

export default function ConfirmDialog({ title, message, onCancel, onConfirm, loading }) {
    return (
        <Dialog open onClose={onCancel}>
            <DialogTitle>{title}</DialogTitle>
            <DialogContent>
                <DialogContentText>{message}</DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={onCancel}>Cancel</Button>
                <Button color="error" variant="contained" onClick={onConfirm} disabled={loading}>
                    {loading ? 'Deleting…' : 'Delete'}
                </Button>
            </DialogActions>
        </Dialog>
    )
}