import { html, LitElement } from 'lit';

class MyTestElement extends LitElement {

  static get properties() {
    return {
      // title: { type: String },
      // text: { type: String },
      // btn_text: { type: String }
    }
  }

  render() {
    return html`
    <div class="card" style="width: 18rem;">
  <img src="https://codingyaar.com/wp-content/uploads/bootstrap-profile-card-image.jpg" class="card-img-top" alt="...">
  <div class="card-body">
    <h5 class="card-title">Coding Yaar</h5>
    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
    <a href="#" class="btn btn-primary">Know More</a>
  </div>
</div>

<p class="mt-5 text-center">Get a step-by-step written explanation here: <a href="https://codingyaar.com/bootstrap-profile-card-1/
" target="_blank">Bootstrap Profile Card</a> </p>

<p class="mt-5 text-center">Get a step-by-step video explanation here: <a href="https://youtu.be/Y7vGhu3GJpg" target="_blank">Bootstrap Profile Card</a> </p>
    `;
  }
}

window.customElements.define('my-test-element', MyTestElement);