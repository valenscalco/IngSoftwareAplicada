import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PublisherComponent } from './list/publisher.component';
import { PublisherDetailComponent } from './detail/publisher-detail.component';
import { PublisherUpdateComponent } from './update/publisher-update.component';
import { PublisherDeleteDialogComponent } from './delete/publisher-delete-dialog.component';
import { PublisherRoutingModule } from './route/publisher-routing.module';

@NgModule({
  imports: [SharedModule, PublisherRoutingModule],
  declarations: [PublisherComponent, PublisherDetailComponent, PublisherUpdateComponent, PublisherDeleteDialogComponent],
})
export class PublisherModule {}
