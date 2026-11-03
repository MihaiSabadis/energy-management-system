import { Navigate } from 'react-router'
import { useAuth } from './useAuth'

export default function ProtectedRoute({ role, children }) {
    const { auth } = useAuth()
    if (!auth) return <Navigate to="/login" replace />
    if (role && auth.role !== role) return <Navigate to="/" replace />
    return children
}