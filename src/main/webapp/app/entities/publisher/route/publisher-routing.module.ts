import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PublisherComponent } from '../list/publisher.component';
import { PublisherDetailComponent } from '../detail/publisher-detail.component';
import { PublisherUpdateComponent } from '../update/publisher-update.component';
import { PublisherRoutingResolveService } from './publisher-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const publisherRoute: Routes = [
  {
    path: '',
    component: PublisherComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PublisherDetailComponent,
    resolve: {
      publisher: PublisherRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PublisherUpdateComponent,
    resolve: {
      publisher: PublisherRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PublisherUpdateComponent,
    resolve: {
      publisher: PublisherRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(publisherRoute)],
  exports: [RouterModule],
})
export class PublisherRoutingModule {}
