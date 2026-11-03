import { Navigate } from 'react-router'
import { useAuth } from '../auth/useAuth'

export default function HomeRedirect() {
    const { auth } = useAuth()
    return <Navigate to={auth.role === 'ADMIN' ? '/admin/users' : '/my-devices'} replace />
}