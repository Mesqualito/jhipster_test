import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Chooser, IChooser } from 'app/shared/model/chooser.model';
import { ChooserService } from './chooser.service';
import { ChooserComponent } from './chooser.component';
import { ChooserDetailComponent } from './chooser-detail.component';
import { ChooserUpdateComponent } from './chooser-update.component';

@Injectable({ providedIn: 'root' })
export class ChooserResolve implements Resolve<IChooser> {
  constructor(private service: ChooserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChooser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((chooser: HttpResponse<Chooser>) => {
          if (chooser.body) {
            return of(chooser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Chooser());
  }
}

export const chooserRoute: Routes = [
  {
    path: '',
    component: ChooserComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'radiobuttonsApp.chooser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ChooserDetailComponent,
    resolve: {
      chooser: ChooserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'radiobuttonsApp.chooser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ChooserUpdateComponent,
    resolve: {
      chooser: ChooserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'radiobuttonsApp.chooser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ChooserUpdateComponent,
    resolve: {
      chooser: ChooserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'radiobuttonsApp.chooser.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
