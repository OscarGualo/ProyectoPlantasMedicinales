import { createBrowserRouter, RouterProvider } from 'react-router'
import { Diseno } from './componentes/Diseno'
import { Cargando } from './componentes/Estado'
import { ProveedorSesion } from './sesion/ContextoSesion'
import { RutaLider } from './sesion/RutaLider'

// Rutas del doc 06 §8. Cada pantalla se descarga al visitarla (división del código por ruta): el mapa con
// Leaflet y la gestión del líder no pesan en la primera carga de quien solo consulta una ficha.
const enrutador = createBrowserRouter([
  {
    element: <Diseno />,
    HydrateFallback: () => <Cargando />,
    children: [
      { path: '/', lazy: async () => ({ Component: (await import('./paginas/Mapa')).Mapa }) },
      { path: '/huertos/:id', lazy: async () => ({ Component: (await import('./paginas/FichaHuerto')).FichaHuerto }) },
      {
        path: '/huertos/:id/3d',
        lazy: async () => {
          const { EnConstruccion } = await import('./paginas/Simples')
          return { Component: () => <EnConstruccion titulo="Recorrido 3D del huerto" /> }
        },
      },
      {
        path: '/huertos/:id/reto',
        lazy: async () => {
          const { EnConstruccion } = await import('./paginas/Simples')
          return { Component: () => <EnConstruccion titulo="Reto Documentación lunar" /> }
        },
      },
      {
        path: '/calendario',
        lazy: async () => {
          const { EnConstruccion } = await import('./paginas/Simples')
          return { Component: () => <EnConstruccion titulo="Calendario lunar" /> }
        },
      },
      { path: '/ingresar', lazy: async () => ({ Component: (await import('./paginas/Ingresar')).Ingresar }) },
      {
        element: <RutaLider />,
        children: [
          {
            path: '/gestion',
            lazy: async () => ({ Component: (await import('./paginas/gestion/Gestion')).Gestion }),
            children: [
              {
                index: true,
                lazy: async () => ({ Component: (await import('./paginas/gestion/GestionHuerto')).GestionHuerto }),
              },
              {
                path: 'plantas',
                lazy: async () => ({ Component: (await import('./paginas/gestion/GestionPlantas')).GestionPlantas }),
              },
              {
                path: 'cultivos',
                lazy: async () => ({ Component: (await import('./paginas/gestion/GestionCultivos')).GestionCultivos }),
              },
              {
                path: 'cultivos/:id/bitacora',
                lazy: async () => {
                  const { EnConstruccion } = await import('./paginas/Simples')
                  return { Component: () => <EnConstruccion titulo="Bitácora de siembras" /> }
                },
              },
            ],
          },
        ],
      },
      { path: '*', lazy: async () => ({ Component: (await import('./paginas/Simples')).NoEncontrado }) },
    ],
  },
])

export function App() {
  return (
    <ProveedorSesion>
      <RouterProvider router={enrutador} />
    </ProveedorSesion>
  )
}
