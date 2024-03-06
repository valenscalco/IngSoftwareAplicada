import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPublisher } from '../publisher.model';

@Component({
  selector: 'jhi-publisher-detail',
  templateUrl: './publisher-detail.component.html',
})
export class PublisherDetailComponent implements OnInit {
  publisher: IPublisher | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ publisher }) => {
      this.publisher = publisher;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
