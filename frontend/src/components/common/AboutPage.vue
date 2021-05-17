<template>
  <section id="about">
    <!-- <h1 class="about__h1">MONEYJAM</h1> -->
    <div class="stars"></div>

<div class="centered"><span class="cyberspace" data-text="MONEY">MONEY</span>
  <span class="cyberspace" data-text="JAM">JAM</span>
  
</div>

<svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
  <defs>
    <filter id="extrude">
      
      <feMorphology operator="erode" radius="0" in="SourceGraphic" result="erode" />
      <feMorphology operator="erode" radius="2" in="SourceGraphic" result="erode1" />
      <feMorphology operator="erode" radius="3" in="SourceGraphic" result="erode2" />
      <feMorphology operator="erode" radius="4" in="SourceGraphic" result="erode3" />
      <feMorphology operator="erode" radius="6" in="SourceGraphic" result="erode4" />
       <feComposite in="erode" in2="erode1" operator="out" result="main"/> 
      <feComposite in="erode1" in2="erode2" operator="out" result="stroke1"/>
      <feComposite in="erode2" in2="erode3" operator="out" result="stroke2"/>
      <feComposite in="erode3" in2="erode4" operator="out" result="stroke3"/>
      <feGaussianBlur in="stroke1" stdDeviation="0 10" result="stroke1-blur" />
      <feBlend in="stroke1-blur" mode="screen" result="stroke1-blur-blend"></feBlend>
      <feGaussianBlur in="stroke2" stdDeviation="0 10"  />
      <feOffset dx="0" dy="10" result="stroke2-blur"/>
      <feBlend in="stroke2-blur" mode="screen" result="stroke2-blur-blend"></feBlend>
      <feGaussianBlur in="stroke3" stdDeviation="0 25"  />
      <feOffset dx="0" dy="20" result="stroke3-blur"/>
      <feBlend in="stroke3-blur" mode="screen" result="stroke3-blur-blend"></feBlend>
      
      <feFlood result="floodFill" flood-color="rgba(0,0,0,0.7)" flood-opacity="1"/>
      <feComposite in="floodFill" in2="erode2" operator="in" result="black"/> 
      <feBlend in="black" mode="screen" result="letterInside"></feBlend>
      <feMerge>
        <feMergeNode in="stroke1-blur-blend"></feMergeNode>
        <feMergeNode in="stroke2-blur-blend"></feMergeNode>
      <feMergeNode in="stroke3-blur-blend"></feMergeNode>
        <feMergeNode in="main"></feMergeNode>
        <feMergeNode in="letterInside"></feMergeNode>
      </feMerge>
      
    </filter>
    <filter id="extrude1">
      
      <feOffset dx="1" dy="3" in="SourceGraphic" result="L1"/>
      <feMorphology operator="erode" radius="1" in="L1" result="L2" />
      <feOffset dx="1" dy="10" in="L2" result="L3"/>

 
 <feMerge result="trail">
   <feMergeNode in="L1" />
   <feMergeNode in="L3" />
</feMerge>
<feGaussianBlur in="trail" stdDeviation="3 0" result="trail-blur" />
<feMerge>
   <feMergeNode in="trail-blur" />
   <feMergeNode in="SourceGraphic" />
</feMerge>
  </filter>
  </defs>
</svg>

  </section>
</template>

<script>
export default {

}
</script>


<style lang="scss" scoped>
// @import '@/sass/components/about';
#about{
  background:radial-gradient(#050526 0%,black 90%) -20vw 10vh no-repeat black;
  
  margin:0;
  padding:0;
  width:100vw;
  height:100vh;
  position:absolute;  
  perspective: 340px;
  height: 100%;
  overflow: hidden;
}

.centered{
  position:absolute;
  left:50vw;
  top:50vh;
  transform:translateX(-50%) translateY(-50%) rotateX(15deg);
  text-align:center;
  
}

.cyberspace{
  position:relative;
  font-family:'Cyberspace-Raceway-Back',sans-serif;
  font-size:4rem;
  color:black;
  -webkit-clip:background;
  -webkit-background-clip:text;
  -webkit-text-fill-color:rgba(135,209,228,1);
  -webkit-text-stroke-width: 0.1rem;
   -webkit-text-stroke-color: rgba(135,209,228,1); 
  filter:url(#extrude);
  
}


$stars: 350;         // Number of start per layer
$depth: 300;         // Depth between star layers
$speed: 10s;          // Number of seconds to transition between layers
$width: 3000;        // Width of the starfield
$height: 960;        // Height of the starfield


.stars {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 2px;
  height: 2px;
  $box-shadow: ();
  @for $i from 0 through $stars {
    $box-shadow: $box-shadow, (random($width)-$width/2 + px) (random($height)-$height/2 + px) hsl(90,0,75+random(25));
  }
  box-shadow: $box-shadow;
  animation: fly $speed linear infinite;
  transform-style: preserve-3d;
  
  &:before, &:after {
    content: "";
    position: absolute;
    width: inherit;
    height: inherit;
    box-shadow: inherit;
  }
  &:before {
    transform: translateZ(-$depth + px);
    animation: fade1 $speed linear infinite;
  }
  &:after {
    transform: translateZ(-$depth * 2 + px);
    animation: fade2 $speed linear infinite;
  }
}

@keyframes fly {
  from {
    transform: translateZ(0px);
  }
  to {
    transform: translateZ($depth + px);
  }
}

@keyframes fade1 {
  from {
    opacity: .5;
  }
  to {
    opacity: 1;
  }  
}
@keyframes fade2 {
  from {
    opacity: 0;
  }
  to {
    opacity: .5;
  }  
}

</style>

