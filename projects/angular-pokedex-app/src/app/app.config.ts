import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { PokemonListComponent } from './pokemon/pokemon-list/pokemon-list.component';
import { provideRouter, Routes } from '@angular/router';
import { PokemonProfile } from './pokemon/pokemon-profile/pokemon-profile';
import { PageNotFoundComponent } from './page-not-found/page-not-found';
import { PokemonEditComponent } from './pokemon/pokemon-edit/pokemon-edit.component';
import { provideHttpClient } from '@angular/common/http';
import { authGuard } from './core/auth/auth.guard';
import { LoginComponent } from './login/login';
import { PokemonAddComponent } from './pokemon/pokemon-add/pokemon-add';
import { environment } from '../environments/environment';
import { PokemonLocalStorageService } from './pokemon-local-storage.service';
import { PokemonJSONServerService } from './pokemon-json-server.service';
import { PokemonService } from './pokemon.service';


export function pokemonServiceFactory(): PokemonService {
  return environment.production
    ? new PokemonLocalStorageService()
    : new PokemonJSONServerService();
}
export const routes: Routes = [
  
  
  {path: 'login', 
    component: LoginComponent, 
    title: 'Page de connexion'}, 
 
  {
    path: 'pokemons',
    canActivate: [authGuard],
    children:[
      {
    path: 'add',
    component: PokemonAddComponent,
    title: "Ajout d'un Pokémon",
  },
       {
    path: 'edit/:id',
     component: PokemonEditComponent,
      title: "Edition d'un Pokémon",
  },
   {   path: ':id',
     component: PokemonProfile,
     title: 'Pokémon'},

     
     {path: '', 
    component: PokemonListComponent, 
    title: 'pokédex',  },
  
  
    ]
    
  },
  // Define your application routes here
  {path: '', redirectTo: '/pokemons', pathMatch: 'full'},
  {path: '**', component: PageNotFoundComponent}
];

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({eventCoalescing: true}),
    provideRouter(routes),
    provideHttpClient(),
   {
      provide: PokemonService,
      useFactory: pokemonServiceFactory,
    },
  ]
};


