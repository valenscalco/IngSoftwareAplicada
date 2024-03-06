import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPublisher } from '../publisher.model';
import { PublisherService } from '../service/publisher.service';

@Injectable({ providedIn: 'root' })
export class PublisherRoutingResolveService implements Resolve<IPublisher | null> {
  constructor(protected service: PublisherService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPublisher | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((publisher: HttpResponse<IPublisher>) => {
          if (publisher.body) {
            return of(publisher.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
