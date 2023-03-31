import { HomeComponent } from "../home/home.component";
import { AuthComponent } from "../auth/auth.component";
import { MainComponent } from "../main/main.component";

const routes = [
    { path: 'home', component: HomeComponent, pathMatch: 'full' as const},
    { path: 'auth', component: AuthComponent, pathMatch: 'full' as const},
    { path: '', redirectTo: 'home', pathMatch: 'full' as const},
    { path: 'authorized', redirectTo: 'auth', pathMatch: 'full' as const},
    { path: 'main', component: MainComponent, pathMatch: 'full' as const},
];

export default routes;